import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './lead-upload-file.reducer';
import { ILeadUploadFile } from 'app/shared/model/lead-upload-file.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const LeadUploadFileUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const leadUploadFileEntity = useAppSelector(state => state.leadUploadFile.entity);
  const loading = useAppSelector(state => state.leadUploadFile.loading);
  const updating = useAppSelector(state => state.leadUploadFile.updating);
  const updateSuccess = useAppSelector(state => state.leadUploadFile.updateSuccess);

  const handleClose = () => {
    props.history.push('/lead-upload-file');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...leadUploadFileEntity,
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
          ...leadUploadFileEntity,
          uploadStatus: 'Pending',
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="campaignToolApp.leadUploadFile.home.createOrEditLabel" data-cy="LeadUploadFileCreateUpdateHeading">
            <Translate contentKey="campaignToolApp.leadUploadFile.home.createOrEditLabel">Create or edit a LeadUploadFile</Translate>
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
                  id="lead-upload-file-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('campaignToolApp.leadUploadFile.fileUrl')}
                id="lead-upload-file-fileUrl"
                name="fileUrl"
                data-cy="fileUrl"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.leadUploadFile.uploadStatus')}
                id="lead-upload-file-uploadStatus"
                name="uploadStatus"
                data-cy="uploadStatus"
                type="select"
              >
                <option value="Pending">{translate('campaignToolApp.UploadStatus.Pending')}</option>
                <option value="InProgress">{translate('campaignToolApp.UploadStatus.InProgress')}</option>
                <option value="Completed">{translate('campaignToolApp.UploadStatus.Completed')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('campaignToolApp.leadUploadFile.createdBy')}
                id="lead-upload-file-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.leadUploadFile.createdAt')}
                id="lead-upload-file-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
              />
              <ValidatedField
                label={translate('campaignToolApp.leadUploadFile.updatedBy')}
                id="lead-upload-file-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
              />
              <ValidatedField
                label={translate('campaignToolApp.leadUploadFile.updatedAt')}
                id="lead-upload-file-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lead-upload-file" replace color="info">
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

export default LeadUploadFileUpdate;
