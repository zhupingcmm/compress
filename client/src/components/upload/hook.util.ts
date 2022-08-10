import { useMutation } from '@tanstack/react-query';
import { useHttp } from './../../utils/http';

interface CompressProfile {
    height?: number;
    width?: number;
    angle?: number;
}

interface Compress {
    pictureIds?: number[],
    compressProfile?: CompressProfile
}
export const useCompressPicture = () => {
    const client = useHttp();
    return useMutation((data?:Partial<Compress>) => client('', {method: 'POST', data}), {
        onSuccess(data: any, variables: any, context?: any) {
            console.log(data);
        }
    })
}