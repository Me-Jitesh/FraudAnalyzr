## 🛸Real-Time Fraud Detection System

> Kafka + Kafka Streams + Spring Boot | Event-Driven Architecture | React

A real-time transaction fraud detection system built using Apache Kafka Streams and Spring Boot.
The system processes financial transactions as a live stream, detects suspicious activity, and publishes fraud alerts
instantly.

> ✨ Key Features

⚡ Real-time transaction processing

🚨 Rule-based fraud detection

🔄 Kafka Streams topology

📊 Live stream processing status

🌐 REST APIs for alerts & monitoring

🐳 Docker-ready backend

> 📂 Core Flow

<pre>
Transactions API
↓
Kafka Topic (transactions)
↓
Kafka Streams Processor
↓
Kafka Topic (fraud-alerts)
↓
REST APIs / UI
</pre>

> API

- POST /api/v1/txn/pay
- GET /api/v1/txn/pay/bulk
- GET /api/v1/fraud/alerts
- GET /api/v1/stream/status

> SAMPLE cURL

<pre>
curl -X POST http://localhost:8080/api/v1/txn/pay \
-H "Content-Type: application/json" \
-d '{
"transactionId":"TXN101",
"accountId":"ACC001",
"amount":150000,
"merchant":"Amazon",
"timestamp":"2025-01-12T10:00:00Z"
}'
</pre>
