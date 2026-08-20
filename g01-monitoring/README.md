# g01-monitoring

The monitoring side of the g01 delivery chain: Prometheus, Grafana and a
blackbox exporter behind Traefik, deployed to its own VM by its own pipeline.
`docker-compose.yml` is what ran there; `docker_local.yml` is the cut-down
version described below.

To see this stack running against the actual application without the lab
network, use [devops-stack](../devops-stack) instead.

## Local Development

This guide assumes your separate application stack (like `g01-form`) is already running and exposing metrics on `http://localhost:9187`.

### 1. Configure Prometheus

Prometheus needs to know where to find your application's metrics.

1.  The local stack mounts `./prometheus/prometheus_local.yml` — not
    `prometheus.yml`, which holds the lab targets used by the deployment.
2.  It already contains the `local-postgres` job below, so there is nothing to
    change unless your exporter runs somewhere else. The special name
    `host.docker.internal` allows the container to find your `localhost`.

    ```yaml
    global:
      scrape_interval: 15s

    scrape_configs:
      - job_name: 'prometheus'
        static_configs:
          - targets: ['localhost:9090']

      # This job scrapes your application
      - job_name: 'local-postgres'
        static_configs:
          # This target points to port 9187 on your host machine
          - targets: ['host.docker.internal:9187']
    ```

### 2. Run with Docker Compose

With the configuration saved, start the monitoring stack:

```bash
docker compose -f docker_local.yml up -d
```

### 3. Access Your Services

* **Grafana UI:** `http://localhost:3000`
    * Default Login: `admin` / `admin`
* **Prometheus UI:** `http://localhost:9090`
    * Check **Status** > **Targets** here to ensure your `local-postgres` job is **UP** (green).

### 4. Configure Grafana Data Source

You only need to do this once.

1.  Log in to Grafana (`http://localhost:3000`).
2.  Go to the **Gear icon (⚙️) > Data Sources**.
3.  Click **Add data source** and select **Prometheus**.
4.  In the **URL** field, enter: `http://prometheus:9090`
    * (This name works because `prometheus` is the container's service name in the same Docker network).
5.  Click **Save & Test**. You should see a green "Data source is working" message.


