import React, { FC } from "react";
import notFountImage from "@src/assets/404.png";
import { Button, Typography } from "antd";
import { Link } from "react-router-dom";

export const NotFound = () => {
  return (
    <div>
      <div>
        <img src={notFountImage} alt="404" />
      </div>
      <div>
        <Typography.Text>404</Typography.Text>
        <Typography.Text>
          Sorry, the page you visited does not exist!
        </Typography.Text>
        <Button type="primary">
          <Link to="/">Back to Home</Link>
        </Button>
      </div>
    </div>
  );
};
