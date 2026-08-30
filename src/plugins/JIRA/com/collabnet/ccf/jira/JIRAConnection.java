package com.collabnet.ccf.jira;

import java.net.URI;
import java.net.URISyntaxException;

import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;
import com.collabnet.ccf.core.eis.connection.ConnectionManager;
import com.microsoft.tfs.core.TFSTeamProjectCollection;

public class JIRAConnection {

    private String         username;
    private String         password;
    private JiraRestClient jiraRestClient;

    public JIRAConnection(String repositoryId, String repositoryKind,
            String serverUrl, String credentialInfo,
            ConnectionManager<JIRAConnection> connectionManager)
            throws URISyntaxException {
        if (null != credentialInfo) {
            String[] splitCredentials = credentialInfo
                    .split(JIRAConnectionFactory.PARAM_DELIMITER);
            if (null != splitCredentials) {
                if (1 == splitCredentials.length) {
                    username = splitCredentials[0];
                    password = "";
                } else if (2 == splitCredentials.length) {
                    username = splitCredentials[0];
                    password = splitCredentials[1];
                } else {
                    throw new IllegalArgumentException(
                            "Credentials info is not valid.");
                }
            }
        }

        final URI jiraServerUri = new URI(serverUrl);
        final JerseyJiraRestClientFactory factory = new JerseyJiraRestClientFactory();
        final JiraRestClient restClient = factory
                .createWithBasicHttpAuthentication(jiraServerUri, username,
                        password);
        setJiraRestClient(restClient);

    }

    public JiraRestClient getJiraRestClient() {
        return jiraRestClient;
    }

    public TFSTeamProjectCollection getTpc() {
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setJiraRestClient(JiraRestClient jiraRestClient) {
        this.jiraRestClient = jiraRestClient;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
