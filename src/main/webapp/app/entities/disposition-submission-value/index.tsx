import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DispositionSubmissionValue from './disposition-submission-value';
import DispositionSubmissionValueDetail from './disposition-submission-value-detail';
import DispositionSubmissionValueUpdate from './disposition-submission-value-update';
import DispositionSubmissionValueDeleteDialog from './disposition-submission-value-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DispositionSubmissionValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DispositionSubmissionValueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DispositionSubmissionValueDetail} />
      <ErrorBoundaryRoute path={match.url} component={DispositionSubmissionValue} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DispositionSubmissionValueDeleteDialog} />
  </>
);

export default Routes;
