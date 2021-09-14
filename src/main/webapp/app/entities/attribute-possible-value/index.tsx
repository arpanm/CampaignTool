import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AttributePossibleValue from './attribute-possible-value';
import AttributePossibleValueDetail from './attribute-possible-value-detail';
import AttributePossibleValueUpdate from './attribute-possible-value-update';
import AttributePossibleValueDeleteDialog from './attribute-possible-value-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AttributePossibleValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AttributePossibleValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AttributePossibleValueDetail} />
      <ErrorBoundaryRoute path={match.url} component={AttributePossibleValue} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AttributePossibleValueDeleteDialog} />
  </>
);

export default Routes;
