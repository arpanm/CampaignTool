import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LeadAssignment from './lead-assignment';
import LeadAssignmentDetail from './lead-assignment-detail';
import LeadAssignmentUpdate from './lead-assignment-update';
import LeadAssignmentDeleteDialog from './lead-assignment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LeadAssignmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LeadAssignmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LeadAssignmentDetail} />
      <ErrorBoundaryRoute path={match.url} component={LeadAssignment} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LeadAssignmentDeleteDialog} />
  </>
);

export default Routes;
