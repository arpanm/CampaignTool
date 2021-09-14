import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './campaign.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CampaignDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const campaignEntity = useAppSelector(state => state.campaign.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="campaignDetailsHeading">
          <Translate contentKey="campaignToolApp.campaign.detail.title">Campaign</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="campaignToolApp.campaign.name">Name</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="campaignToolApp.campaign.description">Description</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.description}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="campaignToolApp.campaign.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {campaignEntity.startDate ? <TextFormat value={campaignEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="campaignToolApp.campaign.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {campaignEntity.endDate ? <TextFormat value={campaignEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="type">
              <Translate contentKey="campaignToolApp.campaign.type">Type</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.type}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="campaignToolApp.campaign.status">Status</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.status}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="campaignToolApp.campaign.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="campaignToolApp.campaign.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="campaignToolApp.campaign.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {campaignEntity.createdAt ? <TextFormat value={campaignEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="campaignToolApp.campaign.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="campaignToolApp.campaign.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {campaignEntity.updatedAt ? <TextFormat value={campaignEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="campaignToolApp.campaign.disposition">Disposition</Translate>
          </dt>
          <dd>{campaignEntity.disposition ? campaignEntity.disposition.id : ''}</dd>
          <dt>
            <Translate contentKey="campaignToolApp.campaign.client">Client</Translate>
          </dt>
          <dd>{campaignEntity.client ? campaignEntity.client.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/campaign" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/campaign/${campaignEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CampaignDetail;
