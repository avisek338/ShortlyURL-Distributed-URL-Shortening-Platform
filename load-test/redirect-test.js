// redirect-test.js
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '10s', target: 20 },   // ramp up
    { duration: '30s', target: 100 },  // sustained load
    { duration: '10s', target: 0 },    // ramp down
  ],
};

export default function () {
  const res = http.get('http://localhost:8082/87'); // use a real existing short code
  check(res, { 'status is 200 or 302': (r) => r.status === 200 || r.status === 302 });
  sleep(0.5);
}