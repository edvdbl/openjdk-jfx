/*
 * Copyright (C) 2017 Apple Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY APPLE INC. ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL APPLE INC. OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#include "config.h"
#include "JSWebGPURenderPassAttachmentDescriptor.h"

#if ENABLE(WEBGPU)

#include "JSDOMBinding.h"
#include "JSWebGPURenderPassColorAttachmentDescriptor.h"
#include "JSWebGPURenderPassDepthAttachmentDescriptor.h"
#include "WebGPURenderPassColorAttachmentDescriptor.h"
#include "WebGPURenderPassDepthAttachmentDescriptor.h"

namespace WebCore {

JSC::JSValue toJSNewlyCreated(JSC::ExecState*, JSDOMGlobalObject* globalObject, Ref<WebGPURenderPassAttachmentDescriptor>&& object)
{
    if (object->isColorAttachmentDescriptor())
        return createWrapper<WebGPURenderPassColorAttachmentDescriptor>(globalObject, WTFMove(object));
    return createWrapper<WebGPURenderPassDepthAttachmentDescriptor>(globalObject, WTFMove(object));
}

JSValue toJS(ExecState* state, JSDOMGlobalObject* globalObject, WebGPURenderPassAttachmentDescriptor& object)
{
    return wrap(state, globalObject, object);
}

}

#endif
