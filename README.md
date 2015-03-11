JIRA-Gerrit integration plugin
==============================
Forked from MeetMe, Inc. Motivation in this fork to implement multiple instance support.

Original Maintainer: Joe Hansche <jhansche@meetme.com>
Fork Maintainer: Baris Inanc baris<at-please-no-spam>barbardata.com


Licensing
---------
Please see the file named LICENSE.


Install
-------
* Available in the [JIRA Marketplace](https://marketplace.atlassian.com/plugins/com.meetme.plugins.jira.gerrit-plugin)
  for automatic installation from the JIRA "Find New Plugins" administration
  panel.
* Alternatively, you can also download the JAR from the above URL, and install
  it manually from the "Manage Plugins" administration panel.


Getting Started
---------------
* Generate an SSH keypair for the JIRA user
  * Save the private key in a place where you can upload it to JIRA (1)
  * The public key will simply be copied/pasted (2)
* Create a new user in Gerrit that you will use for the integration:

        $ ssh gerrit.company.com -p 29418 gerrit create-account jira --email jira@company.com --name JIRA --ssh-key <paste public key (2)>

* In JIRA, navigate to Administration > Plugins > Gerrit Admin
* Enter hostname, port, and user under the SSH section
* Upload the SSH private key generated at (1) above
* Optionally change the Gerrit search query patterns:
  * `tr:%s` - Look for the issue key in a "Bug:" or "Issue:" footer
  * `topic:%s` - Look for the issue key in the change Topic (uploaded using
    `HEAD:refs/for/master/(issueKey)`)
  * `message:%s` - Look for the issue key in the commit message


Features
--------
* "Gerrit Reviews" issue tab panel to show all reviews related to the issue
* "Gerrit Subtask Reviews" issue tab panel to show reviews related to all
  the issue's subtasks
* Workflow condition to require that an issue must (or must not) have any
  open reviews
* Workflow condition to require that an issue must (or must not) have a certain
  approval score (e.g., Code-Review == 2, or ! Verified < 0)
* Workflow function to perform a Gerrit review
  * This is just an argument to the `gerrit review [ChangeId] ..` command, so
    it could be something like `--verified +1` to give a +1 score;
    or `--submit` to submit the change
  * The Gerrit user configured in the admin panel must have access to perform
    all necessary steps

TODO
----
* Support for multiple instances.
