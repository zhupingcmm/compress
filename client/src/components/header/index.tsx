import { Button, Col, Dropdown, Menu, Row, Space } from "antd";
import React from "react";
import { Link } from "react-router-dom";
import logo from "../../assets/logo.png";
import { Switch } from 'antd';
import { GithubOutlined, DownOutlined } from "@ant-design/icons";

type ImageType = "gif" | "png" | "jpg" | "webp";

interface ImageMenuDataItem {
  type: ImageType;
  link: string;
}
const imageMenuDataList: ImageMenuDataItem[] = [
  {
    type: "jpg",
    link: "/compress/jpg",
  },
  {
    type: "png",
    link: "/compress/png",
  },
  {
    type: "gif",
    link: "/compress/gif",
  },
  {
    type: "webp",
    link: "/compress/webp",
  },
];
export enum FileExtraIconNameMapping {
  jpg = "#icon-JPG",
  png = "#icon-PNG",
  gif = "#icon-GIF",
  webp = "#icon-webp1",
}
export const Header = () => {
  return (
    <header className="header">
      <Row>
        <Col span={4}>
          <div className="logo">
            <img width={"80%"} height={"80%"} src={logo} alt="logo" />
            <h2>Compress</h2>
          </div>
        </Col>
        <Col span={14}>
          <Menu mode="horizontal" defaultSelectedKeys={["image"]} className="menu">
            <Menu.SubMenu key={"image"} title={"Compress Image"}>
              {imageMenuDataList.map(({ type, link }) => (
                <Menu.Item key={type}>
                  <Link to={link}>{type}</Link>
                </Menu.Item>
              ))}
            </Menu.SubMenu>
          </Menu>
        </Col>
        <Col span={6} className='operation'>
          <Link to="https://www.github.com/qufei1993">
            <GithubOutlined /> Github
          </Link>
          <Dropdown
            overlay={
              <Menu>
                <Menu.Item key={"zh"}>简体中文</Menu.Item>
                <Menu.Item key={"en"}>English</Menu.Item>
              </Menu>
            }
          >
            <Button onClick={(e) => e.preventDefault()} type="link">
              简体中文
              <DownOutlined />
            </Button>
          </Dropdown>
          <Switch/>
        </Col>
      </Row>
    </header>
  );
};
