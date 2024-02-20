import { NativeModules, Platform } from 'react-native';
import type { requireLocalAuthOptions } from './types';

const LINKING_ERROR =
  `The package 'react-native-secure' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;

const SecureModule = isTurboModuleEnabled
  ? require('./NativeSecure').default
  : NativeModules.Secure;

const Secure = SecureModule
  ? SecureModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function requireLocalAuth(
  options: requireLocalAuthOptions
): Promise<void> {
  return Secure.requireLocalAuth(options);
}

export function canAuthenticate(): void {
  return Secure.canAuthenticate();
}
