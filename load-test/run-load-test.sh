#!/bin/bash

# Script para executar teste de carga e monitorar auto scaling

set -e

BASE_URL="http://a51ab1a444f364a748a180b5ad06a969-1431806275.us-east-1.elb.amazonaws.com/"

# Cores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${GREEN}=== Teste de Carga e Auto Scaling ===${NC}\n"

echo -e "${GREEN}URL Base: $BASE_URL${NC}\n"

# Iniciar monitoramento do HPA em background
echo -e "${GREEN}Iniciando monitoramento do HPA...${NC}"
kubectl get hpa garage-app-hpa -w > hpa-monitor.log 2>&1 &
HPA_PID=$!

# Aguardar um momento
sleep 2

# Executar teste de carga
echo -e "${GREEN}Iniciando teste de carga...${NC}\n"
BASE_URL=$BASE_URL k6 run k6-load-test.js

# Parar monitoramento
kill $HPA_PID 2>/dev/null || true

# Parar port-forward se foi iniciado
if [ ! -z "$PORT_FORWARD_PID" ]; then
    kill $PORT_FORWARD_PID 2>/dev/null || true
fi

# Mostrar resultados do HPA
echo -e "\n${GREEN}=== Histórico do HPA ===${NC}"
tail -20 hpa-monitor.log

# Mostrar pods finais
echo -e "\n${GREEN}=== Pods após teste ===${NC}"
kubectl get pods -l app=garage-app

echo -e "\n${GREEN}Teste concluído!${NC}"
echo -e "Log completo do HPA salvo em: hpa-monitor.log"
