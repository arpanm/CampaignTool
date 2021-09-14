import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TelecallerInOut from './telecaller-in-out';
import TelecallerInOutDetail from './telecaller-in-out-detail';
import TelecallerInOutUpdate from './telecaller-in-out-update';
import TelecallerInOutDeleteDialog from './telecaller-in-out-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TelecallerInOutUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TelecallerInOutUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TelecallerInOutDetail} />
      <ErrorBoundaryRoute path={match.url} component={TelecallerInOut} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TelecallerInOutDeleteDialog} />
  </>
);

export default Routes;
