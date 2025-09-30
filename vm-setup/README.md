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
7. Copy step 1 into a clipboard
### Setup runner 
``` bash
sudo curl -L --output /usr/local/bin/gitlab-runner \
https://gitlab-runner-downloads.s3.amazonaws.com/latest/binaries/gitlab-runner-linux-amd64

sudo chmod +x /usr/local/bin/gitlab-runner

sudo useradd --comment 'GitLab Runner' --create-home gitlab-runner --shell /bin/bash

sudo gitlab-runner install --user=gitlab-runner --working-directory=/home/gitlab-runner

sudo gitlab-runner start

> paste the copied stuff from the previous step
> select shell when asked
> done!
```



