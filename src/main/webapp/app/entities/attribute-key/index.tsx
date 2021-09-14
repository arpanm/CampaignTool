import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AttributeKey from './attribute-key';
import AttributeKeyDetail from './attribute-key-detail';
import AttributeKeyUpdate from './attribute-key-update';
import AttributeKeyDeleteDialog from './attribute-key-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AttributeKeyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AttributeKeyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AttributeKeyDetail} />
      <ErrorBoundaryRoute path={match.url} component={AttributeKey} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AttributeKeyDeleteDialog} />
  </>
);

export default Routes;
