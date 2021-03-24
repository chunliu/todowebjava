# todowebjava

A simple sample to demonstrate how to access Azure Key Vault with Azure AD pod identity and the managed identity in Java. 

To build the code and the docker image:

```bash
mvn package -DskipTests
docker build -t todowebjava .
```

To deploy it to an AKS cluster:

1. Update the `aks-deployment.yaml` with the pod identity binding, key vault url, and the client id of the managed identity.
2. In the key vault, create the following 3 secrets:
    - `jdbcUrl` - The JDBC url of the Azure SQL database.
    - `jdbcUsername` - The user name of the db user.
    - `jdbcPassword` - The password of the db user. 
3. Make sure the managed identity has the permission to access the secrets. 
