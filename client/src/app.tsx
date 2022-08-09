import React from "react";
import { Button, message,  } from 'antd';
import { Upload } from '@src/components/Uploads';
import { useHttp } from "./utils/http";
export const App = () => {
    return (
        <div>
            <Upload/>
            <Button href="http://localhost:8091/picture/1" type="link" >download</Button>
        </div>
    )
}