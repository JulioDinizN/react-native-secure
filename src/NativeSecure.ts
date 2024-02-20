import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
import type { requireLocalAuthOptions } from './types';

export interface Spec extends TurboModule {
  requireLocalAuth(options: requireLocalAuthOptions): Promise<void>;
  canAuthenticate(): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('Secure');
