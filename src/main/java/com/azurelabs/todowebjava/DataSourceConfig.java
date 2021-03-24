package com.azurelabs.todowebjava;

import javax.sql.DataSource;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.AzureCliCredential;
import com.azure.identity.AzureCliCredentialBuilder;
import com.azure.identity.ChainedTokenCredential;
import com.azure.identity.ChainedTokenCredentialBuilder;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    public DataSource dataSource() {
        if (secretClient == null) {
            secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUrl)
                .credential(getCredential())
                .buildClient();
        }

        return DataSourceBuilder
                .create()
                .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                .url(secretClient.getSecret("jdbcUrl").getValue())
                .username(secretClient.getSecret("jdbcUsername").getValue())
                .password(secretClient.getSecret("jdbcPassword").getValue())
                .build();
    }

    @Value("${keyvault.url}")
    private String keyVaultUrl;
    @Value("${managedIdentity.clientId}")
    private String managedIdentityClientId;

    private SecretClient secretClient = null;

    private TokenCredential getCredential() {
        AzureCliCredential cliCredential = new AzureCliCredentialBuilder().build();
        
        ManagedIdentityCredential miCredential = new ManagedIdentityCredentialBuilder()
            .clientId(managedIdentityClientId)
            .build();

        ChainedTokenCredential chainedCredential = new ChainedTokenCredentialBuilder()
            .addLast(miCredential)
            .addLast(cliCredential)
            .build();
        
        return chainedCredential;
    }
}
