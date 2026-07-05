import http from 'k6/http';
import { check } from 'k6';

const CREATE_URL = 'http://localhost:8081/api/v1/urls';
const REDIRECT_BASE = 'http://localhost:8082';

export const options = {
  scenarios: {
    redirect_test: {
      executor: 'constant-arrival-rate',

      rate: 500,            // Requests per second
      timeUnit: '1s',

      duration: '2m',

      preAllocatedVUs: 100,
      maxVUs: 500,
    },
  },

  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<100'],
  },
};

export function setup() {
  const codes = [];
  const numCodesToGenerate = 10000;

  console.log(`Generating ${numCodesToGenerate} short URLs...`);

  for (let i = 0; i < numCodesToGenerate; i++) {

    const payload = JSON.stringify({
      longUrl: `https://example.com/product/${i}`
    });

    const res = http.post(
      CREATE_URL,
      payload,
      {
        headers: {
          'Content-Type': 'application/json',
        },
      }
    );

    check(res, {
      'URL created': (r) => r.status === 200 || r.status === 201,
    });

    if (res.status === 200 || res.status === 201) {
      const body = JSON.parse(res.body);
      codes.push(body.shortUrlCode);
    }
  }

  console.log(`Generated ${codes.length} short URLs.`);

  return codes;
}

export default function (codes) {

  const code = codes[Math.floor(Math.random() * codes.length)];

  const res = http.get(
    `${REDIRECT_BASE}/${code}`,
    {
      redirects: 0,
    }
  );

  check(res, {
    'status is 301 or 302': (r) =>
      r.status === 301 || r.status === 302,

    'Location header exists': (r) =>
      r.headers.Location !== undefined,
  });
}