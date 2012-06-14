package com.meetme.plugins.jira.gerrit.data;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;

import com.sonyericsson.hudson.plugins.gerrit.gerritevents.GerritQueryException;
import com.sonyericsson.hudson.plugins.gerrit.gerritevents.GerritQueryHandler;
import com.sonyericsson.hudson.plugins.gerrit.gerritevents.ssh.Authentication;
import com.sonyericsson.hudson.plugins.gerrit.gerritevents.ssh.SshException;

public class IssueReviewsImpl implements IssueReviewsManager {
    private static final String GERRIT_SEARCH = "message:%1$s";

    private GerritConfiguration configuration;

    public IssueReviewsImpl(GerritConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public List<JSONObject> getReviews(String issueKey) throws GerritQueryException {
        Authentication auth = new Authentication(configuration.getSshPrivateKey(), configuration.getSshUsername());
        GerritQueryHandler h = new GerritQueryHandler(configuration.getSshHostname(), configuration.getSshPort(), auth);

        try {
            return h.queryJava(String.format(GERRIT_SEARCH, issueKey), false, true, false);
        } catch (SshException e) {
            e.printStackTrace();
            throw new GerritQueryException("An ssh error occurred while querying for reviews.", e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GerritQueryException("An error occurred while querying for reviews.", e);
        }
    }
}