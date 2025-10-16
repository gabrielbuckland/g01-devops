# VM Setup

### 1. Update Debian
``` bash
sudo apt update && sudo apt upgrade -y
sudo apt install -y git curl ca-certificates gnupg lsb-release
```

### 2. Install curl, git etc
``` bash

sudo install -m 0755 -d /etc/apt/keyrings
```
### 3. Install Docker engine

``` bash
curl -fsSL https://download.docker.com/linux/debian/gpg | \
sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

echo \
"deb [arch=$(dpkg --print-architecture) \
signed-by=/etc/apt/keyrings/docker.gpg] \
https://download.docker.com/linux/debian \
$(lsb_release -cs) stable" | \

sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt update
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo systemctl enable --now docker

docker --version
docker compose version

sudo usermod -aG docker $USER
newgrp docker
```

### Grab runner settings from Gitlab
1. [Go to gitlab CI/CD settings](https://gitlab.switch.ch/hslu/edu/bachelor-computer-science/devops/25hs01/g01/g01-form/-/settings/ci_cd)
2. Open Runners drawer -> Create project runner
3. add tag e.g test-env-runner
4. lock the runner to the project
5. create runner
6. select Linux as OS
7. Copy step the token into a clipboard
### Setup runner 
``` bash
sudo curl -L --output /usr/local/bin/gitlab-runner \
https://gitlab-runner-downloads.s3.amazonaws.com/latest/binaries/gitlab-runner-linux-amd64

sudo chmod +x /usr/local/bin/gitlab-runner

sudo useradd --comment 'GitLab Runner' --create-home gitlab-runner --shell /bin/bash

sudo gitlab-runner install --user=gitlab-runner --working-directory=/home/gitlab-runner
sudo systemctl enable --now gitlab-runner

sudo gitlab-runner register \
  --url "https://gitlab.switch.ch/" \
  --registration-token "<PROJECT_TOKEN>" \
  --executor "docker" \
  --docker-image "docker:28.4.0-cli" \
  --docker-privileged
```

Then add in /etc/gitlab-runner-config.toml
``` toml
# at top if missing:
concurrent = 2
request_concurrency = 2

[[runners]]
  executor = "docker"
  [runners.docker]
    pull_policy = "if-not-present"
    volumes = ["/var/run/docker.sock:/var/run/docker.sock", "/cache"]
```

Then run -> it should show the runners
``` bash
sudo systemctl restart gitlab-runner
sudo gitlab-runner list
sudo systemctl status gitlab-runner --no-pager
```

