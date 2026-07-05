// full-test.js
import http from 'k6/http';
import { check, sleep } from 'k6';

const CREATE_URL = 'http://localhost:8081/api/v1/urls';
const REDIRECT_BASE = 'http://localhost:8082'; // adjust if your redirect service runs elsewhere

// Runs once before the load test starts — generates real short codes via your actual API
export function setup() {
  const codes = [];
  const numCodesToGenerate = 50; // how many unique short codes to create

  for (let i = 0; i < numCodesToGenerate; i++) {
    const payload = JSON.stringify({
      longUrl: `https://example.com/product/${i}?ref=k6-test`
    });

    const params = {
      headers: { 'Content-Type': 'application/json' },
    };

    const res = http.post(CREATE_URL, payload, params);

    if (res.status === 200 || res.status === 201) {
      const body = JSON.parse(res.body);
      codes.push(body.shortUrlCode);
    } else {
      console.error(`Failed to create URL #${i}: ${res.status} ${res.body}`);
    }
  }

  console.log(`Generated ${codes.length} short codes for testing`);
  return { codes };
}

// Load test stages
export const options = {
  stages: [
    { duration: '10s', target: 20 },
    { duration: '30s', target: 100 },
    { duration: '10s', target: 0 },
  ],
};

// Runs for every VU/iteration — uses codes generated in setup()
export default function (data) {
  const codes = data.codes;
  const code = codes[Math.floor(Math.random() * codes.length)];

  // redirects: 0 stops k6 from following the redirect to the external longUrl —
  // we only want to measure YOUR server's response time
  const res = http.get(`${REDIRECT_BASE}/${code}`, {
    redirects: 0,
  });

  check(res, {
    'status is 301 or 302': (r) => r.status === 301 || r.status === 302,
    'has Location header': (r) => r.headers['Location'] !== undefined,
  });

  sleep(0.5);
}