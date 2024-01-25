
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNSecureSpec.h"

@interface Secure : NSObject <NativeSecureSpec>
#else
#import <React/RCTBridgeModule.h>

@interface Secure : NSObject <RCTBridgeModule>
#endif

@end
