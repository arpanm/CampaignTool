import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lead-upload-file.reducer';

export const LeadUploadFileDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leadUploadFileEntity = useAppSelector(state => state.leadUploadFile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leadUploadFileDetailsHeading">
          <Translate contentKey="automatedPerformanceTestingApp.leadUploadFile.detail.title">LeadUploadFile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{leadUploadFileEntity.id}</dd>
          <dt>
            <span id="fileUrl">
              <Translate contentKey="automatedPerformanceTestingApp.leadUploadFile.fileUrl">File Url</Translate>
            </span>
          </dt>
          <dd>{leadUploadFileEntity.fileUrl}</dd>
          <dt>
            <span id="uploadStatus">
              <Translate contentKey="automatedPerformanceTestingApp.leadUploadFile.uploadStatus">Upload Status</Translate>
            </span>
          </dt>
          <dd>{leadUploadFileEntity.uploadStatus}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="automatedPerformanceTestingApp.leadUploadFile.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{leadUploadFileEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="automatedPerformanceTestingApp.leadUploadFile.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {leadUploadFileEntity.createdAt ? (
              <TextFormat value={leadUploadFileEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="automatedPerformanceTestingApp.leadUploadFile.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{leadUploadFileEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="automatedPerformanceTestingApp.leadUploadFile.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {leadUploadFileEntity.updatedAt ? (
              <TextFormat value={leadUploadFileEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/lead-upload-file" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lead-upload-file/${leadUploadFileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeadUploadFileDetail;
