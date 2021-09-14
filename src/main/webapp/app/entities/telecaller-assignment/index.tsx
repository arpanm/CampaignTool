import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TelecallerAssignment from './telecaller-assignment';
import TelecallerAssignmentDetail from './telecaller-assignment-detail';
import TelecallerAssignmentUpdate from './telecaller-assignment-update';
import TelecallerAssignmentDeleteDialog from './telecaller-assignment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TelecallerAssignmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TelecallerAssignmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TelecallerAssignmentDetail} />
      <ErrorBoundaryRoute path={match.url} component={TelecallerAssignment} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TelecallerAssignmentDeleteDialog} />
  </>
);

export default Routes;
