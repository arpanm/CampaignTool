import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FieldPossibleValue from './field-possible-value';
import FieldPossibleValueDetail from './field-possible-value-detail';
import FieldPossibleValueUpdate from './field-possible-value-update';
import FieldPossibleValueDeleteDialog from './field-possible-value-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FieldPossibleValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FieldPossibleValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FieldPossibleValueDetail} />
      <ErrorBoundaryRoute path={match.url} component={FieldPossibleValue} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FieldPossibleValueDeleteDialog} />
  </>
);

export default Routes;
