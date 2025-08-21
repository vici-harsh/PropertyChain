import { useEffect } from 'react';
import { initAOS } from '../utils/templateUtils';

function AOSInitializer() {
  useEffect(() => {
    initAOS();
  }, []);

  return null;
}

export default AOSInitializer;