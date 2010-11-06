package com.matthewlmcclure.jira.plugins;

import java.util.ArrayList;
import java.util.List;

import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.ComponentManager;
import com.atlassian.jira.JiraDataType;
import com.atlassian.jira.JiraDataTypes;
import com.atlassian.jira.jql.operand.QueryLiteral;
import com.atlassian.jira.jql.query.QueryCreationContext;
import com.atlassian.jira.plugin.jql.function.AbstractJqlFunction;
import com.atlassian.jira.util.MessageSet;
import com.atlassian.query.clause.TerminalClause;
import com.atlassian.query.operand.FunctionOperand;
import com.opensymphony.user.User;

public class RankOfFunction extends AbstractJqlFunction
{

	public JiraDataType getDataType() {
		return JiraDataTypes.NUMBER;
	}

	public int getMinimumNumberOfExpectedArguments() {
		return 1;
	}

	public List<QueryLiteral> getValues(QueryCreationContext arg0,
			FunctionOperand operand, TerminalClause arg2) {
		ComponentManager componentManager = ComponentManager.getInstance();
		IssueManager issueManager = componentManager.getIssueManager();  
		Issue issue = issueManager.getIssueObject(operand.getArgs().get(0));
		CustomFieldManager fieldManager = componentManager.getCustomFieldManager();
		CustomField rankField = fieldManager.getCustomFieldObjectByName("Rank");
		List<QueryLiteral> queryLiterals = new ArrayList<QueryLiteral>();
		Double rankDouble = (Double) issue.getCustomFieldValue(rankField);
		Long rank = rankDouble.longValue();
		queryLiterals.add(new QueryLiteral(operand, rank));
		return queryLiterals;
	}

	public MessageSet validate(User arg0, FunctionOperand operand,
			TerminalClause arg2) {
		return validateNumberOfArgs(operand, 1);
	}
}
