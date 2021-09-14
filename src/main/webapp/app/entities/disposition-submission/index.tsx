import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DispositionSubmission from './disposition-submission';
import DispositionSubmissionDetail from './disposition-submission-detail';
import DispositionSubmissionUpdate from './disposition-submission-update';
import DispositionSubmissionDeleteDialog from './disposition-submission-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DispositionSubmissionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DispositionSubmissionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DispositionSubmissionDetail} />
      <ErrorBoundaryRoute path={match.url} component={DispositionSubmission} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DispositionSubmissionDeleteDialog} />
  </>
);

export default Routes;
