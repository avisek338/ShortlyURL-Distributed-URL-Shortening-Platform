import http from "k6/http";
import { check } from "k6";

export const options = {
  scenarios: {
    redirect_test: {
      executor: "constant-arrival-rate",

      rate: 500,          // 500 requests/sec
      timeUnit: "1s",

      duration: "2m",

      preAllocatedVUs: 100,
      maxVUs: 500,
    },
  },

  thresholds: {
    http_req_failed: ["rate<0.01"],
    http_req_duration: ["p(95)<100"],
  },
};

export default function () {
  const res = http.get("http://localhost:8082/87", {
    redirects: 0,
  });

  check(res, {
    "status is 301 or 302": (r) =>
      r.status === 301 || r.status === 302,
  });
}