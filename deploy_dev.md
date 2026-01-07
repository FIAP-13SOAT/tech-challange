Para testar os manifestos Kubernetes localmente sem AWS:

#### 1. Instalar minikube
```bash
# Linux
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube

# macOS
brew install minikube

# Windows
choco install minikube
```

#### 2. Iniciar cluster local
```bash
# Iniciar minikube
minikube start

# Verificar status
kubectl get nodes
```

#### 3. Ajustar ConfigMap para local
```bash
# Editar k8s/configmap.yaml
# Alterar DB_HOST para "host.minikube.internal" (acessa localhost da máquina host)
```

#### 4. Deploy da aplicação
```bash
# Aplicar manifestos
kubectl apply -f k8s/

# Verificar pods
kubectl get pods

# Expor serviço localmente
minikube service garage-service --url
```

#### 5. Acessar aplicação
```bash
# Obter URL local
minikube service garage-service

# Ou usar port-forward
kubectl port-forward service/garage-service 8080:80
# Acesse: http://localhost:8080
```

#### 6. Parar cluster
```bash
minikube stop
minikube delete  # Remove completamente
```
