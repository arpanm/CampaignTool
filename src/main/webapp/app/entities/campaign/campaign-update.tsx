import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IDisposition } from 'app/shared/model/disposition.model';
import { getEntities as getDispositions } from 'app/entities/disposition/disposition.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntity, updateEntity, createEntity, reset } from './campaign.reducer';
import { ICampaign } from 'app/shared/model/campaign.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CampaignUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const dispositions = useAppSelector(state => state.disposition.entities);
  const clients = useAppSelector(state => state.client.entities);
  const campaignEntity = useAppSelector(state => state.campaign.entity);
  const loading = useAppSelector(state => state.campaign.loading);
  const updating = useAppSelector(state => state.campaign.updating);
  const updateSuccess = useAppSelector(state => state.campaign.updateSuccess);

  const handleClose = () => {
    props.history.push('/campaign');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
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
      disposition: dispositions.find(it => it.id.toString() === values.dispositionId.toString()),
      client: clients.find(it => it.id.toString() === values.clientId.toString()),
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
          ...campaignEntity,
          type: 'Telecalling',
          status: 'Pending',
          dispositionId: campaignEntity?.disposition?.id,
          clientId: campaignEntity?.client?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campaignToolApp.campaign.home.createOrEditLabel" data-cy="CampaignCreateUpdateHeading">
            <Translate contentKey="campaignToolApp.campaign.home.createOrEditLabel">Create or edit a Campaign</Translate>
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
                label={translate('campaignToolApp.campaign.name')}
                id="campaign-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.campaign.description')}
                id="campaign-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.campaign.startDate')}
                id="campaign-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
              />
              <ValidatedField
                label={translate('campaignToolApp.campaign.endDate')}
                id="campaign-endDate"
                name="endDate"
                data-cy="endDate"
                type="date"
              />
              <ValidatedField
                label={translate('campaignToolApp.campaign.type')}
                id="campaign-type"
                name="type"
                data-cy="type"
                type="select"
              >
                <option value="Telecalling">{translate('campaignToolApp.CampaignType.Telecalling')}</option>
                <option value="Email">{translate('campaignToolApp.CampaignType.Email')}</option>
                <option value="SMS">{translate('campaignToolApp.CampaignType.SMS')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('campaignToolApp.campaign.status')}
                id="campaign-status"
                name="status"
                data-cy="status"
                type="select"
              >
                <option value="Pending">{translate('campaignToolApp.CampaignApprovalStatus.Pending')}</option>
                <option value="Approved">{translate('campaignToolApp.CampaignApprovalStatus.Approved')}</option>
                <option value="Rejected">{translate('campaignToolApp.CampaignApprovalStatus.Rejected')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('campaignToolApp.campaign.isActive')}
                id="campaign-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('campaignToolApp.campaign.createdBy')}
                id="campaign-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.campaign.createdAt')}
                id="campaign-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('campaignToolApp.campaign.updatedBy')}
                id="campaign-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.campaign.updatedAt')}
                id="campaign-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="campaign-disposition"
                name="dispositionId"
                data-cy="disposition"
                label={translate('campaignToolApp.campaign.disposition')}
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
                name="clientId"
                data-cy="client"
                label={translate('campaignToolApp.campaign.client')}
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
