import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './disposition-submission-value.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DispositionSubmissionValueDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const dispositionSubmissionValueEntity = useAppSelector(state => state.dispositionSubmissionValue.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dispositionSubmissionValueDetailsHeading">
          <Translate contentKey="campaignToolApp.dispositionSubmissionValue.detail.title">DispositionSubmissionValue</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{dispositionSubmissionValueEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="campaignToolApp.dispositionSubmissionValue.value">Value</Translate>
            </span>
          </dt>
          <dd>{dispositionSubmissionValueEntity.value}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campaignToolApp.dispositionSubmissionValue.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{dispositionSubmissionValueEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="campaignToolApp.dispositionSubmissionValue.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {dispositionSubmissionValueEntity.createdAt ? (
              <TextFormat value={dispositionSubmissionValueEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campaignToolApp.dispositionSubmissionValue.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{dispositionSubmissionValueEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="campaignToolApp.dispositionSubmissionValue.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {dispositionSubmissionValueEntity.updatedAt ? (
              <TextFormat value={dispositionSubmissionValueEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="campaignToolApp.dispositionSubmissionValue.dispositionSubmission">Disposition Submission</Translate>
          </dt>
          <dd>{dispositionSubmissionValueEntity.dispositionSubmission ? dispositionSubmissionValueEntity.dispositionSubmission.id : ''}</dd>
          <dt>
            <Translate contentKey="campaignToolApp.dispositionSubmissionValue.field">Field</Translate>
          </dt>
          <dd>{dispositionSubmissionValueEntity.field ? dispositionSubmissionValueEntity.field.id : ''}</dd>
          <dt>
            <Translate contentKey="campaignToolApp.dispositionSubmissionValue.possibleValue">Possible Value</Translate>
          </dt>
          <dd>{dispositionSubmissionValueEntity.possibleValue ? dispositionSubmissionValueEntity.possibleValue.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/disposition-submission-value" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/disposition-submission-value/${dispositionSubmissionValueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DispositionSubmissionValueDetail;
