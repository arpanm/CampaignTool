import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LeadAssociation from './lead-association';
import LeadAssociationDetail from './lead-association-detail';
import LeadAssociationUpdate from './lead-association-update';
import LeadAssociationDeleteDialog from './lead-association-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LeadAssociationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LeadAssociationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LeadAssociationDetail} />
      <ErrorBoundaryRoute path={match.url} component={LeadAssociation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LeadAssociationDeleteDialog} />
  </>
);

export default Routes;
