import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDisposition } from 'app/shared/model/disposition.model';
import { getEntities as getDispositions } from 'app/entities/disposition/disposition.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { ICampaign } from 'app/shared/model/campaign.model';
import { CampaignType } from 'app/shared/model/enumerations/campaign-type.model';
import { CampaignApprovalStatus } from 'app/shared/model/enumerations/campaign-approval-status.model';
import { getEntity, updateEntity, createEntity, reset } from './campaign.reducer';

export const CampaignUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dispositions = useAppSelector(state => state.disposition.entities);
  const clients = useAppSelector(state => state.client.entities);
  const campaignEntity = useAppSelector(state => state.campaign.entity);
  const loading = useAppSelector(state => state.campaign.loading);
  const updating = useAppSelector(state => state.campaign.updating);
  const updateSuccess = useAppSelector(state => state.campaign.updateSuccess);
  const campaignTypeValues = Object.keys(CampaignType);
  const campaignApprovalStatusValues = Object.keys(CampaignApprovalStatus);

  const handleClose = () => {
    navigate('/campaign');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getDispositions({}));
    dispatch(getClients({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...campaignEntity,
      ...values,
      disposition: dispositions.find(it => it.id.toString() === values.disposition.toString()),
      client: clients.find(it => it.id.toString() === values.client.toString()),
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
          type: 'Telecalling',
          status: 'Pending',
          ...campaignEntity,
          disposition: campaignEntity?.disposition?.id,
          client: campaignEntity?.client?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="automatedPerformanceTestingApp.campaign.home.createOrEditLabel" data-cy="CampaignCreateUpdateHeading">
            <Translate contentKey="automatedPerformanceTestingApp.campaign.home.createOrEditLabel">Create or edit a Campaign</Translate>
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
                  id="campaign-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.campaign.name')}
                id="campaign-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.campaign.description')}
                id="campaign-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.campaign.startDate')}
                id="campaign-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.campaign.endDate')}
                id="campaign-endDate"
                name="endDate"
                data-cy="endDate"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.campaign.type')}
                id="campaign-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {campaignTypeValues.map(campaignType => (
                  <option value={campaignType} key={campaignType}>
                    {translate('automatedPerformanceTestingApp.CampaignType.' + campaignType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.campaign.status')}
                id="campaign-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {campaignApprovalStatusValues.map(campaignApprovalStatus => (
                  <option value={campaignApprovalStatus} key={campaignApprovalStatus}>
                    {translate('automatedPerformanceTestingApp.CampaignApprovalStatus.' + campaignApprovalStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.campaign.isActive')}
                id="campaign-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.campaign.createdBy')}
                id="campaign-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.campaign.createdAt')}
                id="campaign-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.campaign.updatedBy')}
                id="campaign-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.campaign.updatedAt')}
                id="campaign-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="campaign-disposition"
                name="disposition"
                data-cy="disposition"
                label={translate('automatedPerformanceTestingApp.campaign.disposition')}
                type="select"
              >
                <option value="" key="0" />
                {dispositions
                  ? dispositions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="campaign-client"
                name="client"
                data-cy="client"
                label={translate('automatedPerformanceTestingApp.campaign.client')}
                type="select"
              >
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/campaign" replace color="info">
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

export default CampaignUpdate;
