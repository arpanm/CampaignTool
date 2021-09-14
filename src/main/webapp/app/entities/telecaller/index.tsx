import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Telecaller from './telecaller';
import TelecallerDetail from './telecaller-detail';
import TelecallerUpdate from './telecaller-update';
import TelecallerDeleteDialog from './telecaller-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TelecallerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TelecallerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TelecallerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Telecaller} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TelecallerDeleteDialog} />
  </>
);

export default Routes;
