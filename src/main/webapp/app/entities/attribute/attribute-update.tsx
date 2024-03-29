import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAttributePossibleValue } from 'app/shared/model/attribute-possible-value.model';
import { getEntities as getAttributePossibleValues } from 'app/entities/attribute-possible-value/attribute-possible-value.reducer';
import { IAttributeKey } from 'app/shared/model/attribute-key.model';
import { getEntities as getAttributeKeys } from 'app/entities/attribute-key/attribute-key.reducer';
import { ILead } from 'app/shared/model/lead.model';
import { getEntities as getLeads } from 'app/entities/lead/lead.reducer';
import { ICampaign } from 'app/shared/model/campaign.model';
import { getEntities as getCampaigns } from 'app/entities/campaign/campaign.reducer';
import { IAttribute } from 'app/shared/model/attribute.model';
import { getEntity, updateEntity, createEntity, reset } from './attribute.reducer';

export const AttributeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const attributePossibleValues = useAppSelector(state => state.attributePossibleValue.entities);
  const attributeKeys = useAppSelector(state => state.attributeKey.entities);
  const leads = useAppSelector(state => state.lead.entities);
  const campaigns = useAppSelector(state => state.campaign.entities);
  const attributeEntity = useAppSelector(state => state.attribute.entity);
  const loading = useAppSelector(state => state.attribute.loading);
  const updating = useAppSelector(state => state.attribute.updating);
  const updateSuccess = useAppSelector(state => state.attribute.updateSuccess);

  const handleClose = () => {
    navigate('/attribute');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAttributePossibleValues({}));
    dispatch(getAttributeKeys({}));
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
      ...attributeEntity,
      ...values,
      value: attributePossibleValues.find(it => it.id.toString() === values.value.toString()),
      key: attributeKeys.find(it => it.id.toString() === values.key.toString()),
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
          ...attributeEntity,
          value: attributeEntity?.value?.id,
          key: attributeEntity?.key?.id,
          lead: attributeEntity?.lead?.id,
          campaign: attributeEntity?.campaign?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="automatedPerformanceTestingApp.attribute.home.createOrEditLabel" data-cy="AttributeCreateUpdateHeading">
            <Translate contentKey="automatedPerformanceTestingApp.attribute.home.createOrEditLabel">Create or edit a Attribute</Translate>
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
                  id="attribute-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.attribute.isActive')}
                id="attribute-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.attribute.createdBy')}
                id="attribute-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.attribute.createdAt')}
                id="attribute-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.attribute.updatedBy')}
                id="attribute-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('automatedPerformanceTestingApp.attribute.updatedAt')}
                id="attribute-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="attribute-value"
                name="value"
                data-cy="value"
                label={translate('automatedPerformanceTestingApp.attribute.value')}
                type="select"
              >
                <option value="" key="0" />
                {attributePossibleValues
                  ? attributePossibleValues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="attribute-key"
                name="key"
                data-cy="key"
                label={translate('automatedPerformanceTestingApp.attribute.key')}
                type="select"
              >
                <option value="" key="0" />
                {attributeKeys
                  ? attributeKeys.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="attribute-lead"
                name="lead"
                data-cy="lead"
                label={translate('automatedPerformanceTestingApp.attribute.lead')}
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
                id="attribute-campaign"
                name="campaign"
                data-cy="campaign"
                label={translate('automatedPerformanceTestingApp.attribute.campaign')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/attribute" replace color="info">
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

export default AttributeUpdate;
