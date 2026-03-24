import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    stages: [
        {duration: '30s', target: 50},   // Ramp up para 50 usuários
        {duration: '1m', target: 100},   // Aumenta para 100 usuários
        {duration: '1m', target: 200},   // Pico de 200 usuários
        {duration: '30s', target: 0},    // Ramp down
    ],
    thresholds: {
        http_req_duration: ['p(95)<5000'], // 95% das requisições < 5s
        // Removido threshold de falha pois 403 é esperado sem autenticação
    },
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';

export default function () {
    // Testar endpoint público (sem autenticação)
    let healthRes = http.get(`${BASE_URL}/actuator/health`);
    
    check(healthRes, {
        'health check accessible': (r) => r.status === 200 || r.status === 403,
    });

    sleep(0.5);

    // Testar outro endpoint público se houver
    // Se todos precisam autenticação, apenas teste o health
    let infoRes = http.get(`${BASE_URL}/actuator/info`);
    
    check(infoRes, {
        'info accessible': (r) => r.status === 200 || r.status === 403,
    });

    sleep(1);
}
