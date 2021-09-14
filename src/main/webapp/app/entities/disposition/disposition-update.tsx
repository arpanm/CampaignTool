import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICampaign } from 'app/shared/model/campaign.model';
import { getEntities as getCampaigns } from 'app/entities/campaign/campaign.reducer';
import { getEntity, updateEntity, createEntity, reset } from './disposition.reducer';
import { IDisposition } from 'app/shared/model/disposition.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DispositionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const campaigns = useAppSelector(state => state.campaign.entities);
  const dispositionEntity = useAppSelector(state => state.disposition.entity);
  const loading = useAppSelector(state => state.disposition.loading);
  const updating = useAppSelector(state => state.disposition.updating);
  const updateSuccess = useAppSelector(state => state.disposition.updateSuccess);

  const handleClose = () => {
    props.history.push('/disposition');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCampaigns({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...dispositionEntity,
      ...values,
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
          ...dispositionEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campaignToolApp.disposition.home.createOrEditLabel" data-cy="DispositionCreateUpdateHeading">
            <Translate contentKey="campaignToolApp.disposition.home.createOrEditLabel">Create or edit a Disposition</Translate>
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
                  id="disposition-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('campaignToolApp.disposition.isActive')}
                id="disposition-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('campaignToolApp.disposition.createdBy')}
                id="disposition-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.disposition.createdAt')}
                id="disposition-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('campaignToolApp.disposition.updatedBy')}
                id="disposition-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.disposition.updatedAt')}
                id="disposition-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/disposition" replace color="info">
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

export default DispositionUpdate;
