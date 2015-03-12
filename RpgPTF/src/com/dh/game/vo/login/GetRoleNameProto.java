// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/proto/login/GetRoleNameProto.proto

package com.dh.game.vo.login;

public final class GetRoleNameProto {
  private GetRoleNameProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface GetRoleNameRequestOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // required int32 headIcon = 1;
    boolean hasHeadIcon();
    int getHeadIcon();
  }
  public static final class GetRoleNameRequest extends
      com.google.protobuf.GeneratedMessage
      implements GetRoleNameRequestOrBuilder {
    // Use GetRoleNameRequest.newBuilder() to construct.
    private GetRoleNameRequest(Builder builder) {
      super(builder);
    }
    private GetRoleNameRequest(boolean noInit) {}
    
    private static final GetRoleNameRequest defaultInstance;
    public static GetRoleNameRequest getDefaultInstance() {
      return defaultInstance;
    }
    
    public GetRoleNameRequest getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.dh.game.vo.login.GetRoleNameProto.internal_static_com_dh_game_vo_login_GetRoleNameRequest_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.dh.game.vo.login.GetRoleNameProto.internal_static_com_dh_game_vo_login_GetRoleNameRequest_fieldAccessorTable;
    }
    
    private int bitField0_;
    // required int32 headIcon = 1;
    public static final int HEADICON_FIELD_NUMBER = 1;
    private int headIcon_;
    public boolean hasHeadIcon() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public int getHeadIcon() {
      return headIcon_;
    }
    
    private void initFields() {
      headIcon_ = 0;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      if (!hasHeadIcon()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt32(1, headIcon_);
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, headIcon_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.dh.game.vo.login.GetRoleNameProto.internal_static_com_dh_game_vo_login_GetRoleNameRequest_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.dh.game.vo.login.GetRoleNameProto.internal_static_com_dh_game_vo_login_GetRoleNameRequest_fieldAccessorTable;
      }
      
      // Construct using com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        headIcon_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest.getDescriptor();
      }
      
      public com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest getDefaultInstanceForType() {
        return com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest.getDefaultInstance();
      }
      
      public com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest build() {
        com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest buildPartial() {
        com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest result = new com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.headIcon_ = headIcon_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest) {
          return mergeFrom((com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest other) {
        if (other == com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest.getDefaultInstance()) return this;
        if (other.hasHeadIcon()) {
          setHeadIcon(other.getHeadIcon());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        if (!hasHeadIcon()) {
          
          return false;
        }
        return true;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              headIcon_ = input.readInt32();
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // required int32 headIcon = 1;
      private int headIcon_ ;
      public boolean hasHeadIcon() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public int getHeadIcon() {
        return headIcon_;
      }
      public Builder setHeadIcon(int value) {
        bitField0_ |= 0x00000001;
        headIcon_ = value;
        onChanged();
        return this;
      }
      public Builder clearHeadIcon() {
        bitField0_ = (bitField0_ & ~0x00000001);
        headIcon_ = 0;
        onChanged();
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:com.dh.game.vo.login.GetRoleNameRequest)
    }
    
    static {
      defaultInstance = new GetRoleNameRequest(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:com.dh.game.vo.login.GetRoleNameRequest)
  }
  
  public interface GetRoleNameResponseOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
    
    // required string name = 1;
    boolean hasName();
    String getName();
  }
  public static final class GetRoleNameResponse extends
      com.google.protobuf.GeneratedMessage
      implements GetRoleNameResponseOrBuilder {
    // Use GetRoleNameResponse.newBuilder() to construct.
    private GetRoleNameResponse(Builder builder) {
      super(builder);
    }
    private GetRoleNameResponse(boolean noInit) {}
    
    private static final GetRoleNameResponse defaultInstance;
    public static GetRoleNameResponse getDefaultInstance() {
      return defaultInstance;
    }
    
    public GetRoleNameResponse getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.dh.game.vo.login.GetRoleNameProto.internal_static_com_dh_game_vo_login_GetRoleNameResponse_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.dh.game.vo.login.GetRoleNameProto.internal_static_com_dh_game_vo_login_GetRoleNameResponse_fieldAccessorTable;
    }
    
    private int bitField0_;
    // required string name = 1;
    public static final int NAME_FIELD_NUMBER = 1;
    private java.lang.Object name_;
    public boolean hasName() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    public String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (com.google.protobuf.Internal.isValidUtf8(bs)) {
          name_ = s;
        }
        return s;
      }
    }
    private com.google.protobuf.ByteString getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8((String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    
    private void initFields() {
      name_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      if (!hasName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getNameBytes());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getNameBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponseOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.dh.game.vo.login.GetRoleNameProto.internal_static_com_dh_game_vo_login_GetRoleNameResponse_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.dh.game.vo.login.GetRoleNameProto.internal_static_com_dh_game_vo_login_GetRoleNameResponse_fieldAccessorTable;
      }
      
      // Construct using com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        name_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse.getDescriptor();
      }
      
      public com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse getDefaultInstanceForType() {
        return com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse.getDefaultInstance();
      }
      
      public com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse build() {
        com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse buildPartial() {
        com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse result = new com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.name_ = name_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse) {
          return mergeFrom((com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse other) {
        if (other == com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse.getDefaultInstance()) return this;
        if (other.hasName()) {
          setName(other.getName());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        if (!hasName()) {
          
          return false;
        }
        return true;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              name_ = input.readBytes();
              break;
            }
          }
        }
      }
      
      private int bitField0_;
      
      // required string name = 1;
      private java.lang.Object name_ = "";
      public boolean hasName() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref).toStringUtf8();
          name_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      public Builder setName(String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        name_ = value;
        onChanged();
        return this;
      }
      public Builder clearName() {
        bitField0_ = (bitField0_ & ~0x00000001);
        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      void setName(com.google.protobuf.ByteString value) {
        bitField0_ |= 0x00000001;
        name_ = value;
        onChanged();
      }
      
      // @@protoc_insertion_point(builder_scope:com.dh.game.vo.login.GetRoleNameResponse)
    }
    
    static {
      defaultInstance = new GetRoleNameResponse(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:com.dh.game.vo.login.GetRoleNameResponse)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_dh_game_vo_login_GetRoleNameRequest_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_dh_game_vo_login_GetRoleNameRequest_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_dh_game_vo_login_GetRoleNameResponse_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_dh_game_vo_login_GetRoleNameResponse_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n&com/proto/login/GetRoleNameProto.proto" +
      "\022\024com.dh.game.vo.login\"&\n\022GetRoleNameReq" +
      "uest\022\020\n\010headIcon\030\001 \002(\005\"#\n\023GetRoleNameRes" +
      "ponse\022\014\n\004name\030\001 \002(\t"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_dh_game_vo_login_GetRoleNameRequest_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_dh_game_vo_login_GetRoleNameRequest_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_dh_game_vo_login_GetRoleNameRequest_descriptor,
              new java.lang.String[] { "HeadIcon", },
              com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest.class,
              com.dh.game.vo.login.GetRoleNameProto.GetRoleNameRequest.Builder.class);
          internal_static_com_dh_game_vo_login_GetRoleNameResponse_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_com_dh_game_vo_login_GetRoleNameResponse_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_dh_game_vo_login_GetRoleNameResponse_descriptor,
              new java.lang.String[] { "Name", },
              com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse.class,
              com.dh.game.vo.login.GetRoleNameProto.GetRoleNameResponse.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}
