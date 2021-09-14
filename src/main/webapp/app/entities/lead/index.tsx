import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Lead from './lead';
import LeadDetail from './lead-detail';
import LeadUpdate from './lead-update';
import LeadDeleteDialog from './lead-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LeadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LeadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LeadDetail} />
      <ErrorBoundaryRoute path={match.url} component={Lead} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LeadDeleteDialog} />
  </>
);

export default Routes;
