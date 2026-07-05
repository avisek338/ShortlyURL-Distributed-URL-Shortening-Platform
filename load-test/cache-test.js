// cache-check.js
import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  vus: 10,
  duration: '15s',
};

export default function () {
  http.get('http://localhost:8082/87'); // use a real existing short code from your DB
  sleep(0.5);
}