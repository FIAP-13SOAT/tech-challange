import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    stages: [
        {duration: '2m', target: 50},   // Ramp up para 50 usuários
        {duration: '5m', target: 100},  // Aumenta para 100 usuários
        {duration: '3m', target: 200},  // Pico de 200 usuários
        {duration: '2m', target: 50},   // Reduz para 50
        {duration: '1m', target: 0},    // Ramp down
    ],
    thresholds: {
        http_req_duration: ['p(95)<2000'], // 95% das requisições < 2s
        http_req_failed: ['rate<0.1'],     // Taxa de erro < 10%
    },
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';

export default function () {
    // Health check
    let healthRes = http.get(`${BASE_URL}/actuator/health`);
    check(healthRes, {
        'health check status 200': (r) => r.status === 200,
    });

    sleep(1);

    // Endpoint de listagem (ajuste conforme sua API)
    let listRes = http.get(`${BASE_URL}/api/clientes`);
    check(listRes, {
        'list status 200 or 401': (r) => r.status === 200 || r.status === 401,
    });

    sleep(2);
}
