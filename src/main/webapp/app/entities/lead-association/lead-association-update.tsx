import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILead } from 'app/shared/model/lead.model';
import { getEntities as getLeads } from 'app/entities/lead/lead.reducer';
import { ICampaign } from 'app/shared/model/campaign.model';
import { getEntities as getCampaigns } from 'app/entities/campaign/campaign.reducer';
import { ILeadAssociation } from 'app/shared/model/lead-association.model';
import { getEntity, updateEntity, createEntity, reset } from './lead-association.reducer';

export const LeadAssociationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const leads = useAppSelector(state => state.lead.entities);
  const campaigns = useAppSelector(state => state.campaign.entities);
  const leadAssociationEntity = useAppSelector(state => state.leadAssociation.entity);
  const loading = useAppSelector(state => state.leadAssociation.loading);
  const updating = useAppSelector(state => state.leadAssociation.updating);
  const updateSuccess = useAppSelector(state => state.leadAssociation.updateSuccess);

  const handleClose = () => {
    navigate('/lead-association');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getLeads({}));
    dispatch(getCampaigns({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...leadAssociationEntity,
      ...values,
      lead: leads.find(it => it.id.toString() === values.lead.toString()),
      campaign: campaigns.find(it => it.id.toString() === values.campaign.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...leadAssociationEntity,
          lead: leadAssociationEntity?.lead?.id,
          campaign: leadAssociationEntity?.campaign?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="automatedPerformanceTestingApp.leadAssociation.home.createOrEditLabel" data-cy="LeadAssociationCreateUpdateHeading">
            <Translate contentKey="automatedPerformanceTestingApp.leadAssociation.home.createOrEditLabel">
              Create or edit a LeadAssociation
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="lead-association-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadAssociation.assignmentDate')}
                id="lead-association-assignmentDate"
                name="assignmentDate"
                data-cy="assignmentDate"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadAssociation.createdBy')}
                id="lead-association-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadAssociation.createdAt')}
                id="lead-association-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadAssociation.updatedBy')}
                id="lead-association-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.leadAssociation.updatedAt')}
                id="lead-association-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="lead-association-lead"
                name="lead"
                data-cy="lead"
                label={translate('automatedPerformanceTestingApp.leadAssociation.lead')}
                type="select"
              >
                <option value="" key="0" />
                {leads
                  ? leads.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="lead-association-campaign"
                name="campaign"
                data-cy="campaign"
                label={translate('automatedPerformanceTestingApp.leadAssociation.campaign')}
                type="select"
              >
                <option value="" key="0" />
                {campaigns
                  ? campaigns.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lead-association" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default LeadAssociationUpdate;
