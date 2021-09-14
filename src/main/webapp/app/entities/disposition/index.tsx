import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Disposition from './disposition';
import DispositionDetail from './disposition-detail';
import DispositionUpdate from './disposition-update';
import DispositionDeleteDialog from './disposition-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DispositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DispositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DispositionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Disposition} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DispositionDeleteDialog} />
  </>
);

export default Routes;
