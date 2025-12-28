#### VMs 
- **[[Elastic Compute Cloud(EC2)]]**:We use the **Amazon Machine Image(AMI)** to define the image we will use (flavor in Openstack) ,EC2 is the backbone of AWS services like S,RDS...
- **Amazon LightSail**: is a managed virtual service (friendly version of EC2)
#### Containers
- **Elastic Container Service(ECS)**:container orchestration service support Docker containers,it lanches a cluster of VMs on EC2s with docker installed (**Docker as a Service**)
-  **Elastic Container Registry(ECR)**:a repo for container images.it is version controlled that's why we say repo
-  **ECS Fargate**: serverless orchestration container service ,same as **ECS** in fact in runs on it but the difference is we pay on-demand per running container(with **ECS** we have to keep a EC2server running even if we don't have containers running) ,here AWS manages the underlying server, so we don't have to scale or upgrade the EC2 server.
-  **Elastic Kubernetes Service(EKS)**:a fully managed kubernetes service (kubernetes as a service).
#### Serverless 
- **AWS Lambda**: is a serverless function service.
#### Higher performance Computing services  
- The Nitro System : A combination of hardware and lightweight hypervisor enabling faster innovation and enhanced security.all new EC2 instances use the Nitro System 
- Bare Metal Instance : we can launch EC2 instance directly on the hardware for better performance( the M5 and R5 EC2 instances can run bare metal)
- Bottlerocket :linux based opensource system that pupose-build by AWS for running containers on VM or bare metal hosts. 
High Perfoemance Computing (HPC) : A cluster of hundreds of thousands of servers with fast connections between each of them with a purpose of boosting computing capacity.
	   - AWS ParallelCluster : is an AWS-supported open source cluster management tool that makes it easy for us to deploy and manage high performance computing (HPC) clusters on AWS.


## Edge and Hybrid Computing
### Edge Computing
when we are able to push computing workload outside if our networks to run close to the destination location
## Hybrid Computing
when we are able to run workloads on both on-premise and AWS Virtual Private Cloud(VPC)
### Services
![Pasted image 20251013142530.png](http://res.cloudinary.com/dwbsnvktk/image/upload/v1762948574/Pasted%20image%2020251013142530.png.png)
### AWS cost management
#### Cost management : how to save money ?
####  Capacity management : how do we meet the demand of traffic and usage though adding upgrading servers ?
![Pasted image 20251013151633.png](http://res.cloudinary.com/dwbsnvktk/image/upload/v1762948563/Pasted%20image%2020251013151633.png.png)