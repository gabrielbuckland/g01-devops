# devops-stack

Runs the whole g01 system on one machine with one command.

The original deployment lived on three VMs in the HSLU lab network: the
application on srv-020 (test) and srv-021 (prod), the monitoring on srv-003.
Those machines are not reachable from outside the school and no longer
available to us, so nothing in `g01-form` or `g01-monitoring` can be started as
written. This directory collapses the three hosts onto one, keeps the routing
and the scrape configuration the deployment actually used, and replaces only
what genuinely cannot exist locally.

The three repositories are not modified. Their compose files, their Dockerfile
and their CI configuration remain exactly as they were deployed. Everything
specific to running locally lives here.

## Requirements

Docker with Compose v2 and roughly 4 GB of memory available to the engine.
Nothing else. No JDK, no Node, no Maven on the host: both applications are
built inside the images.

## Start

```bash
cd devops-stack
docker compose up -d --wait
```

The first run builds the backend and the frontend from source and takes a few
minutes, most of it Maven resolving the Micronaut dependency tree. Later runs
start in seconds.

```bash
docker compose down          # stop, keep the database
docker compose down -v       # stop and discard the database and dashboards
```

## What you get

Everything is behind a single Traefik entry point on port 8081, the same path
based routing the lab VMs used. Set `WEB_PORT` to move it.

| URL                                       | What                                            |
| ----------------------------------------- | ----------------------------------------------- |
| http://localhost:8081/                    | the newsletter form                             |
| http://localhost:8081/api                 | backend, returns a plain text response          |
| http://localhost:8081/api/form            | the entries, as JSON                            |
| http://localhost:8081/grafana             | Grafana, dashboard provisioned, no login needed |
| http://localhost:8081/prometheus          | Prometheus, four targets                        |
| http://localhost:8081/pg-exporter/metrics | the Postgres exporter                           |
| http://localhost:8081/blackbox            | the blackbox exporter                           |

The frontend calls the backend at the relative path `/api/form`, so it only
works behind the proxy. Opening the frontend container directly on port 3000
would render the page but every submission would fail.

## The feature flag

`FormController` delays every POST by ten seconds unless the Unleash flag
`fixloading` is enabled. In the lab that flag came from the GitLab feature
flags API, which needs an instance id we do not have here, and an unreachable
Unleash server means the flag reads as off. Every submission would take ten
seconds and look like a broken form.

The `flags` service answers the one endpoint the Unleash client polls, so the
toggle stays demonstrable. To see what it hides, set `enabled` to `false` in
`flags/www/client/features.json` and submit the form again. The client polls
once per second, so the change takes effect without a restart.

One caveat worth knowing: if the flags service is unreachable at the moment the
client polls, the client backs off to 300 times its poll interval, which is
five minutes. During that window it keeps serving the last value it saw. If the
flag appears stuck, restart the backend rather than waiting.

## What this stack does not do

It does not run the pipeline. `.gitlab-ci.yml` needs a GitLab instance, its
container registry and runners tagged for the individual VMs. None of that can
be reproduced here, and translating it to a different CI system would show a
different pipeline rather than the one that was built.

It does not deploy anything, so it demonstrates neither the deploy jobs nor
their failure behaviour. There is no rollback in the project to demonstrate
either.

It adds a healthcheck to the frontend that the deployed compose files do not
have, because `--wait` needs one to tell when the stack is ready.
