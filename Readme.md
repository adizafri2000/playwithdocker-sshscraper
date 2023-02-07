(WIP) To scrape ssh credentials from [Play with Docker](https://labs.play-with-docker.com/) environment via automation
The main purpose of this tool seems very niche, you can refer it to the [use case](https://github.com/adizafri2000/playwithdocker-sshscraper#use-cases). It might seems useless, but if it can be of any use to you (once it is completed), then it's all good!

## Planned method for use:
1. Clone repository
2. User sets up personal Docker Hub account username and password in a configuration file
3. Run execution command and receive ssh credentials (user and host) in terminal
4. Run ``grep/sed/awk`` or other related commands to change any instances of ``localhost:PORT`` in project folder to replace them with the ssh credentials

### Play with Docker VM instances' SSH credentials and app URL endpoints
When you run a containerized application on Play with Docker, a new port is exposed (depending on your configuration) and when you open that port to 
access the application, it is given a URL generated based on the SSH credentials given by the VM instance. For example, an application deployed on Play with Docker has a URL like:

``http://ip172-18-0-17-cfb5ivn91rrg00dk7lj0-4200.direct.labs.play-with-docker.com``

This can be extracted as:

``http://[SSH USERNAME]-[APPLICATION PORT NUMBER].direct.labs.play-with-docker.com``

## Use case(s?)
For deploying a Docker application via repository cloning to [play with docker](https://labs.play-with-docker.com/), but certain parts of the code contain set localhost API endpoints. So when the app is deployed to play with docker, user needs to reconfigure files that contain the set localhost API endpoints e.g a frontend service sending data to backend service listening on http://localhost:4200 needs the 'localhost:4200' part changed to the play with docker opened port URL.
Based on the [above](https://github.com/adizafri2000/playwithdocker-sshscraper#play-with-docker-vm-instances-ssh-credentials-and-app-url-endpoints) section, this means that the words ``localhost`` and ``4200`` as well as the colon ``:`` needs to be converted to be as similar as the [above](https://github.com/adizafri2000/playwithdocker-sshscraper#play-with-docker-vm-instances-ssh-credentials-and-app-url-endpoints) so that the API endpoints can be correctly configured to run on the play with Docker environment.

For example:

| URL Type         | Scheme  | First part (with port separator)    | Port | The rest                          |
|------------------|---------|-------------------------------------|------|-----------------------------------|
| Original         | http:// | localhost:                          | 4200 |                -                  |
| Play with Docker | http:// | ip172-18-0-17-cfb5ivn91rrg00dk7lj0- | 4200 | .direct.labs.play-with-docker.com |



## Requirements
1. Java 8 and Maven 3.6 (supported version as it was developed with it, newer versions might be supported too, idk)
2. A [Docker Hub](https://hub.docker.com/) account

## Current steps to use
1. Change the ``USERNAME`` and ``PASSWORD`` in the [.properties](https://github.com/adizafri2000/playwithdocker-sshscraper/blob/master/.properties) file to your own Docker Hub credentials
2. Run ``mvn clean install``
3. Output should be generated in a new file called ``sshcred.txt``

### File output
The output will contain two lines:
- Play with Docker VM instance username
- Play with Docker VM instance host (by default: direct.labs.play-with-docker.com)

## Drawbacks
The automation from the beginning will open up a browser and go to [play with docker](https://labs.play-with-docker.com/). When the automation ends and even if the output is generated, the created VM instance in play with Docker will be closed as the browser window is closed too. This means that the purpose of obtaining the VM instance SSH credentials as of now is straight up **pointless**.
Nevertheless, I plan to make this automation compatible or become a component for whenever you have a Dockerizable application and would like to deploy to [play with docker](https://labs.play-with-docker.com/), 
but are now able to skip the entire deployment steps and simply do everything from the command line.

## Automation workflow:
1. Login [play with docker](https://labs.play-with-docker.com/)
2. Enter Docker credentials in separate tab popup
3. Return focus to [play with docker](https://labs.play-with-docker.com/)
4. Click on 'Add new Instance' button
5. Retrieve ssh command text as final output and write it to ``sshcred.txt``

### Involved pages:
#### [Main play with docker](https://labs.play-with-docker.com/)

<img width="950" alt="image" src="https://user-images.githubusercontent.com/61119610/217002423-f0934a78-9839-4ac2-9888-9b107b65864d.png">

#### New tab popup for Docker Hub credentials

<img width="959" alt="image" src="https://user-images.githubusercontent.com/61119610/217002576-6fb11fc5-cf3d-4720-ae7e-778fdc1ba871.png">

#### Play with docker console

<img width="960" alt="image" src="https://user-images.githubusercontent.com/61119610/217002033-86250899-274d-4cbb-a119-b18e194b80f0.png">
