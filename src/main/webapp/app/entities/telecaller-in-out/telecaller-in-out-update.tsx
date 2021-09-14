import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ITelecaller } from 'app/shared/model/telecaller.model';
import { getEntities as getTelecallers } from 'app/entities/telecaller/telecaller.reducer';
import { getEntity, updateEntity, createEntity, reset } from './telecaller-in-out.reducer';
import { ITelecallerInOut } from 'app/shared/model/telecaller-in-out.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TelecallerInOutUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const telecallers = useAppSelector(state => state.telecaller.entities);
  const telecallerInOutEntity = useAppSelector(state => state.telecallerInOut.entity);
  const loading = useAppSelector(state => state.telecallerInOut.loading);
  const updating = useAppSelector(state => state.telecallerInOut.updating);
  const updateSuccess = useAppSelector(state => state.telecallerInOut.updateSuccess);

  const handleClose = () => {
    props.history.push('/telecaller-in-out');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTelecallers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...telecallerInOutEntity,
      ...values,
      telecaller: telecallers.find(it => it.id.toString() === values.telecallerId.toString()),
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
          ...telecallerInOutEntity,
          eventType: 'IN',
          telecallerId: telecallerInOutEntity?.telecaller?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campaignToolApp.telecallerInOut.home.createOrEditLabel" data-cy="TelecallerInOutCreateUpdateHeading">
            <Translate contentKey="campaignToolApp.telecallerInOut.home.createOrEditLabel">Create or edit a TelecallerInOut</Translate>
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
                  id="telecaller-in-out-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('campaignToolApp.telecallerInOut.eventType')}
                id="telecaller-in-out-eventType"
                name="eventType"
                data-cy="eventType"
                type="select"
              >
                <option value="IN">{translate('campaignToolApp.InOutType.IN')}</option>
                <option value="OUT">{translate('campaignToolApp.InOutType.OUT')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('campaignToolApp.telecallerInOut.eventTime')}
                id="telecaller-in-out-eventTime"
                name="eventTime"
                data-cy="eventTime"
                type="date"
              />
              <ValidatedField
                label={translate('campaignToolApp.telecallerInOut.eventDate')}
                id="telecaller-in-out-eventDate"
                name="eventDate"
                data-cy="eventDate"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.telecallerInOut.createdBy')}
                id="telecaller-in-out-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.telecallerInOut.createdAt')}
                id="telecaller-in-out-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('campaignToolApp.telecallerInOut.updatedBy')}
                id="telecaller-in-out-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.telecallerInOut.updatedAt')}
                id="telecaller-in-out-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <ValidatedField
                id="telecaller-in-out-telecaller"
                name="telecallerId"
                data-cy="telecaller"
                label={translate('campaignToolApp.telecallerInOut.telecaller')}
                type="select"
              >
                <option value="" key="0" />
                {telecallers
                  ? telecallers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/telecaller-in-out" replace color="info">
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

export default TelecallerInOutUpdate;
