import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Call from './call';
import CallDetail from './call-detail';
import CallUpdate from './call-update';
import CallDeleteDialog from './call-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CallUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CallUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CallDetail} />
      <ErrorBoundaryRoute path={match.url} component={Call} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CallDeleteDialog} />
  </>
);

export default Routes;
