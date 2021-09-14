import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LeadUploadFile from './lead-upload-file';
import LeadUploadFileDetail from './lead-upload-file-detail';
import LeadUploadFileUpdate from './lead-upload-file-update';
import LeadUploadFileDeleteDialog from './lead-upload-file-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LeadUploadFileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LeadUploadFileUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LeadUploadFileDetail} />
      <ErrorBoundaryRoute path={match.url} component={LeadUploadFile} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LeadUploadFileDeleteDialog} />
  </>
);

export default Routes;
