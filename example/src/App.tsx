import * as React from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import { canAuthenticate, requireLocalAuth } from 'react-native-secure';

export default function App() {
  return (
    <View style={styles.container}>
      <Text>Result:</Text>
      <TouchableOpacity
        onPress={() => {
          canAuthenticate();
        }}
      >
        <Text>Can authenticate?</Text>
      </TouchableOpacity>
      <View style={styles.separator} />
      <TouchableOpacity
        onPress={async () => {
          await requireLocalAuth({
            title: 'title javascript test',
            subtitle: 'Subtitle javascript test',
            negativeButtonText: 'Negative',
          });
        }}
      >
        <Text>Authenticate</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
  separator: {
    height: 50,
  },
});
